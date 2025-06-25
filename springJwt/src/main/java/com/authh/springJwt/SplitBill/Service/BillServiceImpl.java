package com.authh.springJwt.SplitBill.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.authh.springJwt.Authentication.model.User;
import com.authh.springJwt.Authentication.repo.UserRepository;
import com.authh.springJwt.SplitBill.DTO.CreateBillRequest;
import com.authh.springJwt.SplitBill.DTO.ParticipantDTO;
import com.authh.springJwt.SplitBill.Model.Bill;
import com.authh.springJwt.SplitBill.Model.BillParticipant;
import com.authh.springJwt.SplitBill.Repository.BillParticipantRepository;
import com.authh.springJwt.SplitBill.Repository.BillRepository;
import com.authh.springJwt.SplitBill.Response.BillResponse;
import com.authh.springJwt.SplitBill.Response.ParticipantStatus;
import com.authh.springJwt.Wallet.Service.WalletService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepo;
    private final BillParticipantRepository participantRepo;
    private final UserRepository userRepo;
    private final WalletService walletService;

    @Override
    public BillResponse createBill(CreateBillRequest request, String creatorUsername) {
        User creator = userRepo.findByUsername(creatorUsername)
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        System.out.println("split bills _____"+request.getTitle());
        System.out.println("split bills _____"+request.getTotalAmount());
        Bill bill = new Bill();
        bill.setTitle(request.getTitle());
        bill.setTotalAmount(request.getTotalAmount());
        bill.setCreatedBy(creator);
        bill.setCreatedAt(LocalDateTime.now());

        List<BillParticipant> participants = new ArrayList<>();
        for (ParticipantDTO dto : request.getParticipants()) {
            User participant = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Participant not found"));
            BillParticipant bp = new BillParticipant();
            bp.setBill(bill);
            bp.setUser(participant);
            bp.setAmountOwed(dto.getAmountOwed());
            participants.add(bp);
        }

        bill.setParticipants(participants);
        Bill savedBill = billRepo.save(bill);
        return convertToResponse(savedBill);
    }

    @Override
    public List<BillResponse> getMyBills(String username) {
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        List<BillParticipant> entries = participantRepo.findByUser_Id(user.getId());
        Set<Bill> bills = entries.stream().map(BillParticipant::getBill).collect(Collectors.toSet());
        System.out.println("get my bills___"+bills);
        return bills.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public BillResponse getBillDetails(Long billId) {
        Bill bill = billRepo.findById(billId)
            .orElseThrow(() -> new RuntimeException("Bill not found"));
        return convertToResponse(bill);
    }

    @Override
    public String settleBill(Long billId, String username,String mpin,String note,Boolean isUseReward) {
        User payer = userRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        BillParticipant bp = participantRepo.findByBill_IdAndUser_Id(billId, payer.getId())
            .orElseThrow(() -> new RuntimeException("No bill found for user"));

        if (bp.getHasPaid()) {
            return "Already paid.";
        }
        String senderNumber=payer.getNumber();
        String receiverNumber=bp.getBill().getCreatedBy().getNumber();
        Double amount=bp.getAmountOwed();
  
        try {
            walletService.transferFunds(
                senderNumber,
                receiverNumber,
               amount,
                mpin, note, isUseReward);
        } catch (IOException e) {
            throw new RuntimeException("Payment failed: " + e.getMessage());
        }


        bp.setHasPaid(true);
        bp.setPaidAt(LocalDateTime.now());
        participantRepo.save(bp);
        return "Payment successful.";
    }

    private BillResponse convertToResponse(Bill bill) {
        
       List<ParticipantStatus> parts = bill.getParticipants().stream().map(bp -> {
            ParticipantStatus ps = new ParticipantStatus();
            ps.setUserId(bp.getUser().getId());
            
            String fullName = bp.getUser().getFirstname() + " " + bp.getUser().getLastname();
            ps.setName(fullName);
            
            ps.setAmountOwed(bp.getAmountOwed());
            ps.setHasPaid(bp.getHasPaid());
            ps.setPaidAt(bp.getPaidAt());
            return ps;
        }).collect(Collectors.toList());


       BillResponse response = new BillResponse();

        String creatorFullName = bill.getCreatedBy().getFirstname() + " " + bill.getCreatedBy().getLastname();

        response.setBillId(bill.getId());
        response.setTitle(bill.getTitle());
        response.setCreatedBy(creatorFullName); // full name here
        response.setTotalAmount(bill.getTotalAmount());
        response.setCreatedAt(bill.getCreatedAt());
        response.setParticipants(parts);

        return response;

    }
}

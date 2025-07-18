package com.authh.springJwt.SplitBill.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.authh.springJwt.WebSocket.Service.NotificationService;


@Service
// @RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    @Autowired
    private  BillRepository billRepo;
    @Autowired
    private  BillParticipantRepository participantRepo;
    @Autowired
    private  UserRepository userRepo;
    @Autowired
    private  WalletService walletService;
    @Autowired
    private NotificationService notificationService;
    // @Autowired
    // private SplitBillMapper splitbillMapper;


    //////////////////////////////////////////////////////////////////////////////////
    /// Creating bill split

    @Override
    public BillResponse createBill(CreateBillRequest request, Long creatorId) {
        User creator = userRepo.findById(creatorId)
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
        System.out.println("split bills _____"+dto); 
        System.out.println("split bills _____"+dto.getPhoneNumber()); 

            User participant = userRepo.findByNumber(dto.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("Participant not found"));
        System.out.println("participant is _____"+participant.getFirstname()); 
                
            BillParticipant bp = new BillParticipant();
            // BillParticipant bp = splitBillMapper.toBillParticipant(dto);
            bp.setBill(bill);
            bp.setUser(participant);
            bp.setAmountOwed(dto.getAmountOwed());
            participants.add(bp);
        }

        bill.setParticipants(participants);
        Bill savedBill = billRepo.save(bill);
        notificationService.sendBillUpdate(
            "group-" + savedBill.getId(), 
            "A new bill titled '" + savedBill.getTitle() + "' was added.",
            creator.getFirstname(),
            "BILL_ADDED"
        );
        return convertToResponse(savedBill);
    }//okay

    //////////////////////////////////////////////////////////////////////////////////
    /// fetch splitted bills
    @Override
    public List<BillResponse> getMyBills(Long Id) {
        User user = userRepo.findById(Id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        List<BillParticipant> entries = participantRepo.findByUser_Id(user.getId());
        Set<Bill> bills = entries.stream().map(BillParticipant::getBill).collect(Collectors.toSet());
        System.out.println("get my bills___"+bills);
        return bills.stream().map(this::convertToResponse).collect(Collectors.toList());
    }
    //////////////////////////////////////////////////////////////////////////////////
    /// fetch splitted bills details
////okay
    @Override
    public BillResponse getBillDetails(Long billId) {
        Bill bill = billRepo.findById(billId)
            .orElseThrow(() -> new RuntimeException("Bill not found"));
        return convertToResponse(bill);
    }
    //////////////////////////////////////////////////////////////////////////////////
    /// pay the splitted bills

    @Override
    public String settleBill(Long billId, Long userId,String mpin,String note,Boolean isUseReward) {
        User payer = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        BillParticipant bp = participantRepo.findByBill_IdAndUser_Id(billId, userId)
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
        notificationService.sendBillUpdate(
            "group-" + billId,
            payer.getFirstname() + " has settled their part of the bill.",
            payer.getFirstname(),
            "BILL_PAID"
        );
        return "Payment successful.";
    }
    //////////////////////////////////////////////////////////////////////////////////

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
    //////////////////////////////////////////////////////////////////////////////////
}

// package com.authh.springJwt.SplitBill.Mapper;

// import org.mapstruct.Mapper;
// import org.mapstruct.Mapping;

// import com.authh.springJwt.SplitBill.DTO.CreateBillRequest;
// import com.authh.springJwt.SplitBill.DTO.ParticipantDTO;
// import com.authh.springJwt.SplitBill.Model.Bill;
// import com.authh.springJwt.SplitBill.Model.BillParticipant;
// import com.authh.springJwt.SplitBill.Response.BillResponse;

// @Mapper(componentModel="spring")
// public interface SplitBillMapper {

//     @Mapping(source = "createdBy.phoneNumber", target = "createdBy")
//     ParticipantDTO toParticipantDto(BillParticipant  participant);

//     // @Mapping(source = "user.phoneNumber", target = "phoneNumber")
//     @Mapping(target = "user", ignore = true) 
//     @Mapping(target = "bill", ignore = true) 
//     @Mapping(target = "hasPaid", ignore = true)
//     @Mapping(target = "paidAt", ignore = true)
//     BillParticipant toBillParticipant(ParticipantDTO dto);
    
//     CreateBillRequest toBillDto(BillResponse  billResponse);
//     BillResponse toBillResponse(CreateBillRequest  billRequest);
//     BillResponse toBillResponse(Bill  bill);
    
// }

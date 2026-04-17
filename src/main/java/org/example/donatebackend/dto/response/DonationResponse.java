package org.example.donatebackend.dto.response;

import java.util.List;

public class DonationResponse {
    private Long streamerId;
    private String donorName;
    private Double amount;
    private String message;
    private String streamerName;

    private List<TopDonorResponse> topDonors;

    public Long getStreamerId() {
        return streamerId;
    }

    public void setStreamerId(Long streamerId) {
        this.streamerId = streamerId;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStreamerName() {
        return streamerName;
    }

    public void setStreamerName(String streamerName) {
        this.streamerName = streamerName;
    }

    public List<TopDonorResponse> getTopDonors() {
        return topDonors;
    }

    public void setTopDonors(List<TopDonorResponse> topDonors) {
        this.topDonors = topDonors;
    }
}

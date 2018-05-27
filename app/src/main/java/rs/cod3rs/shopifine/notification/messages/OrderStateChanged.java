package rs.cod3rs.shopifine.notification.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import rs.cod3rs.shopifine.domain.BillState;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderStateChanged implements Serializable {

    private Long billId;

    private BillState state;

    public OrderStateChanged() {
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public BillState getState() {
        return state;
    }

    public void setState(BillState state) {
        this.state = state;
    }
}

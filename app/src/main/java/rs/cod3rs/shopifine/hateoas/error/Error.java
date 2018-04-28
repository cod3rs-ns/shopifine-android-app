package rs.cod3rs.shopifine.hateoas.error;

public class Error {

    private String status;
    private String detail;

    public Error() {
        super();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

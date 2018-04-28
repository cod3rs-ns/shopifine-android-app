package rs.cod3rs.shopifine.hateoas.error;

import java.util.List;

public class ErrorResponse {

    private List<Error> errors;
    private Meta meta;

    public ErrorResponse() {
        super();
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}

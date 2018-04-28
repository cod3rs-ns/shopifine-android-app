package rs.cod3rs.shopifine.hateoas;

public class CollectionLinks {

    private final String prev;
    private final String self;
    private final String next;

    public CollectionLinks(final String prev, final String self, final String next) {
        this.prev = prev;
        this.self = self;
        this.next = next;
    }

    public String getPrev() {
        return prev;
    }

    public String getSelf() {
        return self;
    }

    public String getNext() {
        return next;
    }
}

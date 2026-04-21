public class Member {
    private int memberId;
    private String name;

    public Member(int memberId, String name) {
        this.memberId = memberId;
    }

    public int getMemberId() { return memberId; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return   +memberId + "." + name;
    }
}
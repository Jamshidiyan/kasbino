public class User {
    private int mUserId;
    private String mFName;
    private String mLName;
    private int mAge;

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getFName() {
        return mFName;
    }

    public void setFName(String mFName) {
        this.mFName = mFName;
    }

    public String getLName() {
        return mLName;
    }

    public void setLName(String mLName) {
        this.mLName = mLName;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int mAge) {
        this.mAge = mAge;
    }

    public User(String fName, String lName, int mAge) {
        this.mFName = fName;
        this.mLName = lName;
        this.mAge = mAge;
    }

}

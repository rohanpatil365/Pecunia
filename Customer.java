class Customer
{
    String customerName,accountType,contactNo;
    double balance;
    int accountNo;

    Customer(String customerName,String accountType,String contactNo,int accountNo,double balance)
    {
        this.customerName = customerName;
        this.accountType = accountType;
        this.contactNo = contactNo;
        this.accountNo = accountNo;
        this.balance = balance;
    }

    public String getCustomerData()
    {
        return this.accountNo + "," + this.customerName + "," + this.accountType + "," + this.contactNo + "," + this.balance;
    }
}
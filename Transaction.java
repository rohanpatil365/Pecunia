class Transaction
{
    int transactionId,customerAccountNo;
    double accountBal,transactionAmt;
    String transactionType,transactionDate;

    Transaction(int transactionId,int customerAccountNo,String transactionDate,String transactionType,double transactionAmt,double accountBal)
    {
        this.transactionId = transactionId;
        this.customerAccountNo = customerAccountNo;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.transactionAmt = transactionAmt;
        this.accountBal = accountBal;
    }

    public String getTransaction()
    {
        return (this.transactionId + "," + this.customerAccountNo + "," + this.transactionDate + "," + this.transactionType + "," + this.transactionAmt + "," + this.accountBal);
    }
}
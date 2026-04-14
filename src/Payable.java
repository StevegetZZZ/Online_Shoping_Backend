package Hashcode_and_toString;

public interface Payable {
    void pay(double amount);   // оплатить указанную сумму
    double getPay();           // получить общую сумму оплат
}
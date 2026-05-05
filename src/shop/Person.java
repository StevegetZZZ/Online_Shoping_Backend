package shop;

public class Person {
    private String name;
    private double balance;
    private double credit_limit;
    private double credit_used;
    private double credit_must;
    private static final double CREDIT_INTEREST_RATE = 0.1; // 10% ставка

    public Person(String name, double balance, double credit_limit) {
        this.name = name;
        this.balance = balance;
        this.credit_limit = credit_limit;
        this.credit_used = 0;
        this.credit_must = 0;
    }

    // Геттеры
    public String getName() { return name; }

    public double getBalance() { return balance; }

    public void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            throw new IllegalArgumentException("Баланс не может быть отрицательным");
        }
    }

    public double getCredit_limit() { return credit_limit; }

    public void setCredit_limit(double credit_limit) {
        if (credit_limit >= 0) {
            this.credit_limit = credit_limit;
        } else {
            throw new IllegalArgumentException("Кредитный лимит не может быть отрицательным");
        }
    }

    public double getCredit_used() { return credit_used; }

    public double getCredit_must() { return credit_must; }

    public double getAvailableCredit() {
        return credit_limit - credit_used;
    }



    // Оплата наличными
    public boolean charge(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма оплаты должна быть положительной");
        }
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }



    // Оплата в кредит
    public boolean payWithCredit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма кредита должна быть положительной");
        }
        double availableCredit = getAvailableCredit();
        if (availableCredit >= amount) {
            credit_used += amount;
            double interest = amount * CREDIT_INTEREST_RATE;
            credit_must += amount + interest;
            return true;
        }
        return false;
    }



    // Смешанная оплата
    public boolean payMixed(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма оплаты должна быть положительной");
        }

        // Если хватает наличных — оплачиваем полностью наличными
        if (balance >= amount) {
            return charge(amount);
        }

        double cashToUse = balance; // Используем все наличные
        double remaining = amount - cashToUse; // Остаток для оплаты кредитом

        // Проверяем, хватает ли кредитного лимита для остатка
        if (getAvailableCredit() >= remaining) {
            if (cashToUse > 0) {
                charge(cashToUse); // Списываем наличные
            }
            return payWithCredit(remaining); // Оплачиваем остаток кредитом
        } else {
            // Не хватает кредитного лимита даже для остатка
            return false;
        }
    }



    // Погашение кредита
    public boolean repayCredit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма погашения должна быть положительной");
        }
        if (amount >= credit_must) {
            // Полное погашение с возможным излишком
            double excess = amount - credit_must;
            credit_must = 0;
            credit_used = 0;
            balance += excess;
            return true;
        } else {
            // Частичное погашение
            credit_must -= amount;
            // Корректируем использованный кредит если долг стал меньше использованного
            if (credit_must < credit_used) {
                credit_used = credit_must;
            }
            return true;
        }
    }

    // Проверка возможности оплаты
    public boolean canPay(double amount, PaymentService.PaymentMethod method) {
        switch (method) {
            case CASH:
                return balance >= amount;
            case CREDIT:
                return getAvailableCredit() >= amount;
            case MIXED:
                return (balance + getAvailableCredit()) >= amount;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", credit_limit=" + credit_limit +
                ", credit_used=" + credit_used +
                ", credit_must=" + credit_must +
                ", available_credit=" + getAvailableCredit() +
                '}';
    }
}

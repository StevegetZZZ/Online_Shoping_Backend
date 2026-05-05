package shop;

public class PaymentService {

    public enum PaymentMethod {
        CASH,
        CREDIT,
        MIXED
    }

    public static boolean processPayment(Person user, double amount, PaymentMethod method) {
        switch (method) {
            case CASH:
                return user.charge(amount);
            case CREDIT:
                return user.payWithCredit(amount);
            case MIXED:
                return user.payMixed(amount);
            default:
                throw new IllegalArgumentException("Неизвестный метод оплаты");
        }
    }

    public static String getPaymentOptions(Person user, double totalAmount) {
        StringBuilder options = new StringBuilder("Варианты оплаты:\n");
        int optionNumber = 1;

        // Вариант 1: оплата полностью наличными (если хватает)
        if (user.canPay(totalAmount, PaymentService.PaymentMethod.CASH)) {
            options.append(optionNumber++).append(". Наличными (полный платёж)\n");
        }

        // Вариант 2: оплата полностью в кредит (если хватает лимита)
        if (user.canPay(totalAmount, PaymentService.PaymentMethod.CREDIT)) {
            options.append(optionNumber++).append(". В кредит (полный платёж)\n");
        }

        // Вариант 3: смешанная оплата (только если не хватает чего‑то одного, но в сумме хватает)
        boolean canPayMixed = user.canPay(totalAmount, PaymentService.PaymentMethod.MIXED);
        boolean cannotPayCashOnly = !user.canPay(totalAmount, PaymentService.PaymentMethod.CASH);
        boolean cannotPayCreditOnly = !user.canPay(totalAmount, PaymentService.PaymentMethod.CREDIT);

        if (canPayMixed && (cannotPayCashOnly || cannotPayCreditOnly)) {
            double cashToUse = Math.min(user.getBalance(), totalAmount);
            double creditToUse = totalAmount - cashToUse;
            options.append(optionNumber++).append(String.format(". Частично наличными (%.2f руб.) + кредит (%.2f руб.)\n",
                    cashToUse, creditToUse));
        }

        options.append("0. Отмена");
        return options.toString();
    }



}

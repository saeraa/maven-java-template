package org.example;

import java.time.DayOfWeek;

public class FridayDiscount extends BaseDiscount {

    public FridayDiscount(Discount nextDiscount) {
        super(nextDiscount);
    }

    @Override
    protected boolean isApplicable(Product product) {
        return product.getPurchaseDate().getDayOfWeek() == DayOfWeek.FRIDAY;
    }

    @Override
    protected double calculateDiscount(Product product) {
        return product.getPrice() * 0.1;
    }

    @Override
    public String getDescription(Product product) {
        if (isApplicable(product)) {
            return super.description + nextDiscount.getDescription(product) + " 10% off on Friyays! \n";
        }
        return super.description + nextDiscount.getDescription(product);
    }
}

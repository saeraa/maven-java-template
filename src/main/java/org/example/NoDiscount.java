package org.example;

public class NoDiscount extends BaseDiscount {
    public NoDiscount() {
        super(null);
    }

    @Override
    protected boolean isApplicable(Product product) {
        return false;
    }

    @Override
    protected double calculateDiscount(Product product) {
        return 0;
    }
}

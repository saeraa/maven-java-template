package org.example;

abstract class BaseDiscount implements Discount {

    Discount nextDiscount;
    protected String description;

    public BaseDiscount(Discount nextDiscount) {
        this.nextDiscount = nextDiscount;
        this.description = "";
    }

    protected abstract boolean isApplicable(Product product);
    protected abstract double calculateDiscount(Product product);

    @Override
    public double apply(Product product) {
        double discount = 0;
        if (isApplicable(product)) {
            discount = calculateDiscount(product);
        }

        if (nextDiscount != null) {
            discount += nextDiscount.apply(product);
        }

        return discount;
    }

    @Override
    public String getDescription(Product product) {
        return "";
    }
}

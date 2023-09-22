package org.example;

public class QuantityDiscount extends BaseDiscount{

    public QuantityDiscount(Discount nextDiscount) {
        super(nextDiscount);
    }

    @Override
    protected boolean isApplicable(Product product) {
        return product.getQuantity() >= 5;
    }

    @Override
    protected double calculateDiscount(Product product) {
        return product.getQuantity() * 10;
    }

    @Override
        public String getDescription(Product product) {
            if (isApplicable(product)) {
                return super.description +
                        nextDiscount.getDescription(product) +
                        " Quantity discount: 10kr discount per product for buying at least 5 products! \n";
            }
            return super.description + nextDiscount.getDescription(product);
        }

}

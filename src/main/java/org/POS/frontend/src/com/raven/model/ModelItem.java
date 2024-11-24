package org.POS.frontend.src.com.raven.model;

import org.POS.backend.product.ProductType;
import org.POS.backend.product_attribute.ProductAttribute;

import javax.swing.Icon;
import java.util.List;

public class ModelItem {

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Icon getImage() {
        return image;
    }

    public void setImage(Icon image) {
        this.image = image;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public List<ProductAttribute> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(List<ProductAttribute> productAttributes) {
        this.productAttributes = productAttributes;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getVariationId() {
        return variationId;
    }

    public void setVariationId(Integer variationId) {
        this.variationId = variationId;
    }

    public int getVariableTotalQuantity() {
        return variableTotalQuantity;
    }

    public void setVariableTotalQuantity(int variableTotalQuantity) {
        this.variableTotalQuantity = variableTotalQuantity;
    }

    public ModelItem(
            int itemID,
            String itemName,
            String description,
            double price,
            String brandName,
            Icon image,
            ProductType type,
            List<ProductAttribute> productAttributes,
            int quantity,
            Integer variationId,
            int variableTotalQuantity
    ) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.brandName = brandName;
        this.image = image;
        this.type = type;
        this.productAttributes = productAttributes;
        this.quantity = quantity;
        this.variationId = variationId;
        this.variableTotalQuantity = variableTotalQuantity;

    }

    public ModelItem() {
    }

    private int itemID;
    private String itemName;
    private String description;
    private double price;
    private String brandName;
    private Icon image;
    private ProductType type;
    private List<ProductAttribute> productAttributes;
    private int quantity;
    private int variableTotalQuantity;
    private Integer variationId;
}

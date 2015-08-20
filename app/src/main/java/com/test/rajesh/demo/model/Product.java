package com.test.rajesh.demo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rbandala on 7/14/2015.
 */
public class Product {

        @SerializedName("Id")
        @Expose
        private String Id;
        @Expose
        private String Name;
        @Expose
        private String CostPrice;
        @Expose
        private String SellingPrice;
        @Expose
        private String Available;

        /**
         *
         * @return
         * The Id
         */
        public String getId() {
            return Id;
        }

        /**
         *
         * @param Id
         * The -id
         */
        public void setId(String Id) {
            this.Id = Id;
        }

        /**
         *
         * @return
         * The Name
         */
        public String getName() {
            return Name;
        }

        /**
         *
         * @param Name
         * The Name
         */
        public void setName(String Name) {
            this.Name = Name;
        }

        /**
         *
         * @return
         * The CostPrice
         */
        public String getCostPrice() {
            return  CostPrice;
        }

        /**
         *
         * @param CostPrice
         * The CostPrice
         */
        public void setCostPrice(String CostPrice) {
            this.CostPrice = CostPrice;
        }

        /**
         *
         * @return
         * The SellingPrice
         */
        public String getSellingPrice() {
            return SellingPrice;
        }

        /**
         *
         * @param SellingPrice
         * The SellingPrice
         */
        public void setSellingPrice(String SellingPrice) {
            this.SellingPrice = SellingPrice;
        }

        /**
         *
         * @return
         * The Available
         */
        public Boolean getAvailable() {
            if(Available.equalsIgnoreCase("yes"))
            {
                return true;
            }
            else{
                return false;
            }


        }

        /**
         *
         * @param available
         * The Available
         */
                public void setAvailable(boolean available) {
                    if(available){
                        this.Available = "Yes";
                    }
                    else{
                        this.Available = "No";
                    }

        }
}

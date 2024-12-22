package com.kimono.backend.domain.enums;

public enum PaymentMethod {
    //TODO bunlara baklacaklar
    CREDIT_CARD {
        @Override
        public int getCommissionPer1000() {
            // Örneğin 30 olsun
            return 30;
        }
    },
    BANK_TRANSFER {
        @Override
        public int getCommissionPer1000() {
            // Örneğin 10 olsun
            return 10;
        }
    };

    public abstract int getCommissionPer1000();
}

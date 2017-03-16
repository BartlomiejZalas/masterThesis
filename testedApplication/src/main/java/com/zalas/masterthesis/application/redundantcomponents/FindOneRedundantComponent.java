package com.zalas.masterthesis.application.redundantcomponents;

import com.zalas.masterthesis.application.model.Customer;

public interface FindOneRedundantComponent {
    Customer findOne(long id);
}

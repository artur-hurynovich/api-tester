package com.hurynovich.api_tester.model.persistence;

import java.io.Serializable;

public interface Identified<I extends Serializable> {

    I getId();

    void setId(I id);

}

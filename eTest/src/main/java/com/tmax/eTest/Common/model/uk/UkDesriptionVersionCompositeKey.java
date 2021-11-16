package com.tmax.eTest.Common.model.uk;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UkDesriptionVersionCompositeKey implements Serializable {
    private Long ukId;
    private Long versionId;
}

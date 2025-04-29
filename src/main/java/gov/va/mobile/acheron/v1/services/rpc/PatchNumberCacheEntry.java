package gov.va.mobile.acheron.v1.services.rpc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PatchNumberCacheEntry {

    private Integer patchNumber;

    private OffsetDateTime lastUpdated;

}

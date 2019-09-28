package entity;


import lombok.*;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Getter
@RequiredArgsConstructor
public class Flat {
    @NonNull
    private int maxConductivity;

    @Getter(AccessLevel.NONE)
    private List<ElectricalAppliance> appliances;

    public List<ElectricalAppliance> getAppliances() {
        return Collections.unmodifiableList(this.appliances);
    }
}

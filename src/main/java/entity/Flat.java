package entity;


import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Flat {
    @NonNull
    private int maxConductivity;

    @Getter(AccessLevel.NONE)
    private List<ElectricalAppliance> appliances;

    public List<ElectricalAppliance> getAppliances() {
        return Collections.unmodifiableList(this.appliances);
    }

    @Override
    public String toString() {
        return "Flat " + this.hashCode() +
                "\nAppliances: " + appliances.stream()
                .map(appliance -> appliance.getName().toString())
                .collect(Collectors.toList());
    }
}

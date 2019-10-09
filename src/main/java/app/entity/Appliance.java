package app.entity;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public abstract class Appliance {
    private double weight;
    private ApplianceColor color;
    private ApplianceName name;
}

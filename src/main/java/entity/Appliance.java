package entity;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public abstract class Appliance {
    private double weight;
    private ApplianceColor color;
    private ApplianceName name;
}

package entity;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public abstract class Appliance {
    private int id;
    @NonNull
    private double weight;
    @NonNull
    private ApplianceColor color;
    @NonNull
    private ApplianceName name;

    public void setId(int id) {
        this.id = id;
    }
}

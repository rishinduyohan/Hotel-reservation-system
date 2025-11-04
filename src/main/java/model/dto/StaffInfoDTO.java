package model.dto;
import lombok.*;

@NoArgsConstructor
@ToString
@AllArgsConstructor
@Getter
@Setter
public class StaffInfoDTO {
    private String staffId;
    private String name;
    private String telNo;
    private String email;
    private String role;
    private double salary;
}

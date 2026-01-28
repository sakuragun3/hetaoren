package cn.edu.cqrk.hetaoren.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ActiveDto {
    private Integer id;                   // 主键
    private String activityName;          // 患教活动名称
    private String doctorName;            // 主持医生
    private String patientName;           // 患者姓名

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime startTime;          // 开始时间

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime endTime;            // 结束时间

    private Integer participantCount;     // 参加人数
}

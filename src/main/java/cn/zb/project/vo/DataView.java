package cn.zb.project.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataView {
    private Integer code = 0;
    private String msg = "";
    private Long count = 0L;
    private Object data = 0;

    public DataView(Long count, Object data) {
        this.count = count;
        this.data = data;
    }

    public DataView(Object data) {
        this.data = data;
    }
}

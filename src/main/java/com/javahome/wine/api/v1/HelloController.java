package com.javahome.wine.api.v1;

import com.javahome.wine.dto.UserDTO;
import com.javahome.wine.vo.ResultDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.sql.SQLException;


/**
 * @author 勿忘初心
 * @since 2023-06-17-0:34
 */
@RestController
@RequestMapping("/v1/hello")
@Slf4j
@Validated
public class HelloController {

    /**
     * 接口自描述性
     * host:port/v1/hello/msg
     * @return
     */
    @GetMapping("/{id}")
    public ResultDataVO<String> getMsg(@RequestParam(required = false) String name,
                                       @Max(value = 10,message = "最大值不能超过10") @PathVariable(name = "id") int uid)
            throws SQLException {
        log.info("访问hello下的msg方法。");
        String result = "Hello,"+name+" id "+uid;

        return ResultDataVO.success(result);
    }

    @GetMapping("/error")
    public ResultDataVO getError(){
        int res = 1 / 0;
        //throw new BusinessException(ExceptionCodeEnum.EC0);
        return ResultDataVO.success(res);
    }

    @PostMapping("/msg")
    public ResultDataVO postMsg(@RequestBody @Validated UserDTO user){
        return ResultDataVO.success("hello,"+user.getName());
    }

    @PutMapping("/msg")
    public ResultDataVO putMsg(@RequestBody @Validated UserDTO user,
                               @RequestParam Integer code){
        return ResultDataVO.success("hello,"+user.getName()+" code "+code);
    }

    @DeleteMapping("/{id}")
    public ResultDataVO deleteMsg(@PathVariable Integer id){
        return ResultDataVO.success("hello, "+id);
    }
}

import com.bjpn.mapper.ProductInfoMapper;
import com.bjpn.pojo.ProductInfo;
import com.bjpn.pojo.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext_dao.xml")
public class MyTest {
    @Resource
    private ProductInfoMapper productInfoMapper;
    @Test
    public void test01(){
        ProductInfoVo vo = new ProductInfoVo();
        vo.setpName("米米");
        vo.setLprice(3000);
        vo.setHprice(8333);
        vo.setTypeId(2);
        List<ProductInfo> list = productInfoMapper.selectByVo(vo);
        list.forEach(productInfo -> System.out.println(productInfo));
    }
}

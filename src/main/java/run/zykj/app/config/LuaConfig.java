package run.zykj.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @author xiaolin
 * @date 2021/1/5 9:27
 */
@Configuration
public class LuaConfig{

    /**
     * 实际上可以通过jedis调用lua脚本，通过这种方式效率很低
     * @return
     */
    @Bean
    public DefaultRedisScript<Long> defaultRedisScript(){
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/lock.lua")));
        redisScript.setResultType(Long.class);
        return redisScript;
    }

}

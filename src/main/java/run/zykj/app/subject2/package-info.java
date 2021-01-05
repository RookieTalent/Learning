/**
 * 可以直接看lock.lua relocak.lua 还有LuaConfig
 *
 * 这种redis分布式锁实现仍然存在问题
 * 1、存在单点故障问题，官方的回答是redlock算法
 * 2、获取锁失败后只能抛出异常，不能阻塞线程，redisson开源框架解决了这些问题
 *
 *  尝试用AOP的方式实现redis分布式锁
 *
 * @author xiaolin
 * @date 2021/1/5 9:36
 */
package run.zykj.app.subject2;


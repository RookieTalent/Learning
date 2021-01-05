/**
 * 没有屁可以放了，如下
 * 思路：排行榜的实现毫无疑问的是采用zset数据类型，因为有序集合的成员是唯一的,但分数(score)却可以重复
 * 设定排行表的时候为每个音乐网站指定一个点击数字段 然后设定定时任务 在每日的凌晨进行摘取排名在前5的网站：
 * ```mysql
 * select ifnull(
 *     (select distinct 字段 from music_table order by count desc limit 5)
 * , null)
 * 取出后赋给zset，随后访问zset就可以了
 * @author xiaolin
 * @date 2021/1/5 16:25
 */
package run.zykj.app.subject22;
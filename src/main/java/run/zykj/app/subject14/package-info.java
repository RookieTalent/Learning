/**
 *
 * 这个我的想法就比较简单：
 * 1、用户注册数据落库时，status字段的值为init，未激活
 * 2、发送邮件到用户注册邮箱 (邮件服务最好用mq解耦)
 * 3、邮件激活， 可以是验证码， 可以是点击链接， 我就选择简单的验证码好了
 *  redis中的hash格式就是
 *      邮件激活前缀：用户id  code xxxx
 * 4、对比传入的code 修改status字段属性为online， 删除前缀：用户id key
 *
 * @author xiaolin
 * @date 2021/1/5 14:37
 */
package run.zykj.app.subject14;
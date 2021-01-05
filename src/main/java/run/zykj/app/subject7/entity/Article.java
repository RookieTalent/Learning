package run.zykj.app.subject7.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author xiaolin
 * @date 2021/1/5 10:48
 */
@Data
public class Article {

    // id
    private Long id;

    // 文章名称
    private String articleName;

    // 文章内容
    private String content;

    // 文章总点击数
    private Long totalLikeCount;

    // 创建时间
    private LocalDate gmtCreate;

    // 更新时间
    private LocalDate gmtModified;
}

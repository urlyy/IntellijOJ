# jsulyy毕设

# 环境搭建
JDK17!!!!必须

可以参考的
- https://www.zhihu.com/question/20343652

# 基本功能
- 前台
  - 在线编写、运行代码
  - 题库、题目标签
  - 比赛
  - 发布题解和评论(题目内,管理员可设置开关)、讨论区(公共)
  - 比赛排名、刷题排名
  - 不同赛制(OI、ACM)
  - 公告、即时消息通知
  - 个人数据统计
  - 单点登录
  - 每个界面都带一点搜索，字符串模糊匹配、日期过滤
  - 封榜、滚榜
  - 导出题目pdf
  - hitokoto一言
- 后台
  - 用户管理
  - 题库管理
  - 比赛管理、定时发布
  - 判题机管理
  - 题目爬取
  - IP封禁


- 获取github信息 https://api.github.com/users/urlyy
<a href="https://blog.csdn.net/amadeus_liu2/article/details/128888748?ops_request_misc=&request_id=&biz_id=102&utm_term=r2dbc-postgresql%E4%BD%BF%E7%94%A8&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-0-128888748.142^v88^koosearch_v1,239^v2^insert_chatgpt&spm=1018.2226.3001.4187">r2dbc-posgresql<a/>
# 亮点
- 界面像电脑桌面(acwing)
- 不允许异地、多设备访问
- special judge
- 题目推荐
  - 相似标签
  - 根据用户短板的推荐<br>
    对于基于用户的协同过滤算法，可以通过计算用户之间的相似度，找到和目标用户兴趣相似的其他用户，并根据这些用户的刷题情况和评价信息，推荐给目标用户相似的题目。一般而言，可以使用基于余弦相似度或Pearson相似度等指标来计算用户之间的相似度。
    对于基于项目的协同过滤算法，可以根据相似的题目标签或者相似的刷题情况，找到和目标题目相似的其他题目，并根据这些题目的评价信息，推荐给用户。一般而言，可以使用基于余弦相似度或者Jaccard相似度等指标来计算题目之间的相似度。
    此外，还可以使用深度学习模型来进行推荐，例如基于神经网络的推荐模型。这类模型可以学习用户和题目之间的非线性关系，同时还可以利用用户和题目的多种特征信息，提高推荐的准确性和效果。
- 代码相似度检查<br>
  - 使用深度学习模型进行代码表示学习的方法可以较好地考虑代码的语义信息，同时可以处理大规模数据，适用于对代码相似度进行精确判断。一些开源的代码相似度检测工具，如 CodeBERT、Code2Vec 等，都采用了基于代码表示学习的方法，可以在 OJ 中进行应用。
  此外，可以结合其他方法，如基于抽象语法树的方法，对结果进行进一步验证和优化，以提高检查的准确性。同时，为了加快检查速度，可以采用一些优化策略，如使用索引等方法，对代码进行预处理，减少计算量和存储空间。<br>
  - https://blog.csdn.net/weixin_39986741/article/details/110699517?spm=1001.2101.3001.6650.4&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-4-110699517-blog-101130242.235%5Ev35%5Epc_relevant_increate_t0_download_v2&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-4-110699517-blog-101130242.235%5Ev35%5Epc_relevant_increate_t0_download_v2&utm_relevant_index=8
  - https://www.zhihu.com/question/27085271
  - https://blog.csdn.net/chichoxian/article/details/79692867
- 测试数据手动生成(xeger生成字符串、visjs可视化展示网络结构)<br>
  Hypothesis
- 用户能力矩阵
- 代码复杂度分析<br>
  cProfile<br>
  Gprof<br>
  


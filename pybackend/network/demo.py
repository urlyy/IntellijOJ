# 导入nx包和plt包
import networkx as nx
import matplotlib.pyplot as plt

# 添加画布
fig, ax = plt.subplots(1,1)

# 生成图并初始化图的数据
g = nx.DiGraph()
g.add_weighted_edges_from([['a', 'b', 5], ['a', 'c', 4], ['b', 'a', 5], ['b', 'd', 3], ['d', 'c', 1]])

# 生成节点坐标信息，用于画图
pos = nx.spring_layout(g, seed=5)
# 生成边的标签数据，用于绘制边标签
edge_labels = {(d[0],d[1]):d[2]['weight'] for d in g.edges(data=True)}

# 绘制图表，图表有一个整体的draw和四部分组成
nx.draw(g, pos, node_size=[500, 400, 300, 200], node_color='#8888ff', width=5, edge_color='#8888ff')
nx.draw_networkx_edges(g, pos, width=1, edge_color='#0088ff')
nx.draw_networkx_edge_labels(g, pos, edge_labels=edge_labels, font_size=20)
nx.draw_networkx_nodes(g, pos, node_size=150)
nx.draw_networkx_labels(g, pos, font_size=20)

# 给图设置一个标题，并保存
ax.set_title('networkx test')
fig.savefig('./networkx_test.png')
plt.close()
import networkx as nx
from matplotlib import pyplot as plt


g = nx.DiGraph()
g.add_nodes_from([0,1,2,3])
g.add_edges_from([(0,1),(1,2),(2,3)])

fig=plt.figure(figsize=(8,4))    # 设置绘图区域的大小

ax1=fig.add_subplot(2,2,1)       #将绘图区域分为2行2列，共4个区域，这里在第1个区域绘图
ax1.set_title('fig.1: no layout')
nx.draw(g, with_labels=True, node_color='red')

ax2=fig.add_subplot(2,2,2)       #将绘图区域分为2行2列，共4个区域，这里在第2个区域绘图
ax2.set_title('fig. 2: my layout')
pos = {0:(0,0),1:(1,5),2:(2,0),3:(3,5)}
nx.draw(g, pos,with_labels=True, node_color='red')

ax3=fig.add_subplot(2,2,3)        #将绘图区域分为2行2列，共4个区域，这里在第3个区域绘图
ax3.set_title('fig.3: circular layout')
pos = nx.layout.circular_layout(g)
nx.draw(g,pos ,with_labels=True, node_color='red')

ax4=fig.add_subplot(2,2,4)        #将绘图区域分为2行2列，共4个区域，这里在第4个区域绘图
ax4.set_title('fig.4: spiral layout')
pos = nx.layout.planar_layout(g)
nx.draw(g,pos ,with_labels=True, node_color='red')
plt.show()
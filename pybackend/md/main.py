import re

import pandas as pd
from tabulate import tabulate

# 读取本地Markdown文件
with open('C:\\Users\\22137\\Desktop\\data.md', 'r', encoding='utf-8') as file:
    markdown_content = file.read()


# 提取Markdown中的表格内容
table_start = markdown_content.find('|')
table_end = markdown_content.rfind('|')
markdown_table = markdown_content[table_start:table_end]

# 使用正则表达式提取表格数据
table_rows = re.findall(r'\|(.*)\|', markdown_table)
table_data = [row.split('|')[0:-1] for row in table_rows]
print(table_data)

# 提取列名
headers = [header.strip() for header in table_data[0]]
table_data = table_data[1:]

# 构建DataFrame
df = pd.DataFrame(table_data, columns=headers)# 构建DataFrame

# 将数据导出为Excel文件
df.to_excel("table.xlsx", index=False)

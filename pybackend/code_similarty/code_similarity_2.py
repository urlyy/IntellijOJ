import ast
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity

# 代码样本
code1 = "j = 1\nfor i in range(1, 110):\n    j += 2\n    j -= 1"
code2 = "k = 1\nfor i in range(1, 110):\n    k += 1"

# 解析代码为AST
ast1 = ast.parse(code1)
ast2 = ast.parse(code2)

# 提取重要信息（示例中提取变量和循环）
def extract_features(node):
    features = []
    for child in ast.iter_child_nodes(node):
        if isinstance(child, ast.Name):
            features.append(child.id)
        elif isinstance(child, ast.For):
            features.append("FOR_LOOP")
    return features

# 构建代码向量表示
features1 = extract_features(ast1)
print(features1)
features2 = extract_features(ast2)
print(features2)
all_features = list(set(features1 + features2))
code_vector1 = np.array([1 if feature in features1 else 0 for feature in all_features])
code_vector2 = np.array([1 if feature in features2 else 0 for feature in all_features])

# 计算相似度
similarity_score = cosine_similarity([code_vector1], [code_vector2])[0][0]

# 打印相似度
print(f"Similarity between code 1 and code 2: {similarity_score}")

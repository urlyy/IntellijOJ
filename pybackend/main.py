from xeger import Xeger

# 根据表达式生成数据
_x = Xeger()
for i in range(20):
    testStr = _x.xeger(r'[A-H]{3}')
    print(testStr)
    i += 1

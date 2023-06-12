import javalang
import numpy as np

def tokenize(code):
    tokens = []
    for token in javalang.tokenizer.tokenize(code):
        tokens.append(token.value)
    return tokens

def compare_similarity(code1, code2):
    tokens1 = tokenize(code1)
    tokens2 = tokenize(code2)

    intersection = len(set(tokens1) & set(tokens2))
    print(set(tokens1) & set(tokens2))
    union = len(set(tokens1) | set(tokens2))
    similarity = intersection / union
    return similarity

def main():
    code1 = '''
    public class HelloWorld {
        public static void main(String[] args) {
            System.out.println("Hello, World!");
        }
    }
    '''

    code2 = '''
    public     class           Greetings {
        public         static void main(String[] args) {
            System.out.println(    "Greetings, World!"    );
        }
    }
    '''

    similarity = compare_similarity(code1, code2)
    print("Similarity:", similarity)

if __name__ == "__main__":
    main()

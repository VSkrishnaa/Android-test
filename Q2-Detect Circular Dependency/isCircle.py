def hasCircularDependency(n, edges):
    graph = []
    for i in range(n):
        graph.append([])
    
    for edge in edges:
        r = edge[0]
        c = edge[1]
        graph[r].append(c)
    
    visited = [False] * n
    rec_stack = [False] * n
    
    def dfs(node):
        visited[node] = True
        rec_stack[node] = True

        for neighbor in graph[node]:
            if not visited[neighbor]:
                if dfs(neighbor):
                    return True
            elif rec_stack[neighbor]:
                return True
        
        rec_stack[node] = False
        return False
    
    for i in range(n):
        if not visited[i]:
            if dfs(i):
                return True
    
    return False

print("Enter the number of modules:")
n = int(input())

print("Enter the number of dependency relationships:")
num_edges = int(input())

edges = []
print("Enter each dependency as 'a b' (module 'a' depends on module 'b'):")
for i in range(num_edges):
    dependency = input().strip().split()
    a = int(dependency[0])
    b = int(dependency[1])
    edges.append([a, b])

result = hasCircularDependency(n, edges)
print(f"edges = {edges}")
print(f"Output: {result}")

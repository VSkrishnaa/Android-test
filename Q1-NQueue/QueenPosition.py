def nqueens(n):
    def can_place_q(board, row, col):
        for i in range(row):
            if board[i][col] == 'Q':
                return False
        r, c = row - 1, col - 1
        while r >= 0 and c >= 0:
            if board[r][c] == 'Q':
                return False
            r -= 1
            c -= 1
        r, c = row - 1, col + 1
        while r >= 0 and c < n:
            if board[r][c] == 'Q':
                return False
            r -= 1
            c += 1
        
        return True
    
    def place_queens(row):
        if row == n:
            current_solution = []
            for r in board:
                current_solution.append(''.join(r))
            final_solutions.append(current_solution)
            return
        
        for col in range(n):
            if can_place_q(board, row, col):
                board[row][col] = 'Q'
                place_queens(row + 1)
                board[row][col] = '.'  # backtrack
    
    board = []
    for i in range(n):
        row = []
        for j in range(n):
            row.append('.')
        board.append(row)
    
    final_solutions = []
    place_queens(0)
    return final_solutions

n = int(input("Enter the value of n: "))
solutions = nqueens(n)
print(f"Output: {solutions}")
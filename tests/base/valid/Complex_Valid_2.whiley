define PAWN as 0
define KNIGHT as 1 
define BISHOP as 2
define ROOK as 3
define QUEEN as 4
define KING as 5
define PIECE_CHARS as [ 'P', 'N', 'B', 'R', 'Q', 'K' ]

define PieceKind as { PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING }
define Piece as { PieceKind kind, bool colour }

define WHITE_PAWN as { kind: PAWN, colour: true }
define WHITE_KNIGHT as { kind: KNIGHT, colour: true }
define WHITE_BISHOP as { kind: BISHOP, colour: true }
define WHITE_ROOK as { kind: ROOK, colour: true }
define WHITE_QUEEN as { kind: QUEEN, colour: true }
define WHITE_KING as { kind: KING, colour: true }

define BLACK_PAWN as { kind: PAWN, colour: false }
define BLACK_KNIGHT as { kind: KNIGHT, colour: false }
define BLACK_BISHOP as { kind: BISHOP, colour: false }
define BLACK_ROOK as { kind: ROOK, colour: false }
define BLACK_QUEEN as { kind: QUEEN, colour: false }
define BLACK_KING as { kind: KING, colour: false }

define RowCol as int // where 0 <= $ && $ <= 8
define Pos as { RowCol col, RowCol row } 

define SingleMove as { Piece piece, Pos from, Pos to }
define SingleTake as { Piece piece, Pos from, Pos to, Piece taken }
define SimpleMove as SingleMove | SingleTake

define CastleMove as { bool isWhite, bool kingSide }
define CheckMove as { Move check }
define Move as CheckMove | CastleMove | SimpleMove


define A1 as { col: 1, row: 1 }
define A2 as { col: 1, row: 1 }
define A3 as { col: 1, row: 3 }

define D3 as { col: 4, row: 3 }

define H1 as { col: 8, row: 1 }

string move2str(Move m):
    if m is SingleTake: 
        return piece2str(m.piece) + pos2str(m.from) + "x" + piece2str(m.taken) + pos2str(m.to)
    else if m is SingleMove:
        return piece2str(m.piece) + pos2str(m.from) + "-" + pos2str(m.to)   
    else if m is CastleMove:
        if m.kingSide:
            return "O-O"
        else:
            return "O-O-O"
    else:
        // check move
        return move2str(m.check) + "+"  

string piece2str(Piece p):
    if p.kind == PAWN:
        return ""
    else:
        return "" + PIECE_CHARS[p.kind]

string pos2str(Pos p):
    return "" + ('a' + p.col) + ('1' + p.row)


void System::main([string] args):
    m = {piece: WHITE_PAWN, from: A2, to: A1 }
    out.println(move2str(m))
    m = {piece: WHITE_KNIGHT, from: A2, to: A1 }
    out.println(move2str(m))
    m = {piece: WHITE_QUEEN, from: A2, to: A1, taken: BLACK_KING }
    out.println(move2str(m))

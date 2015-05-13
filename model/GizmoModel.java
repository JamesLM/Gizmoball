package model;

public class GizmoModel extends AGizmoModel implements IGizmoModel{
	
	@Override
	public IGizmo createGizmoOp(char type, String opcode, int x, int y){
		setOccupiedSquares();
		
		if (type == 's'){
			boolean temp = false;
			if (gizmoList.size() > 0){
				for (Coordinate c : occupiedSquares){
					if (c.x == x && c.y == y){
						temp = true;
					}
				}
				if (temp == false){
					return new SquareBumper(opcode, x, y, this);
				} else 
					return null;
			} else {
				return new SquareBumper(opcode, x, y, this);
			}
		} else if (type == 'c'){
			boolean temp = false;
			if (gizmoList.size() > 0){
				for (Coordinate c : occupiedSquares){
					if (c.x == x && c.y == y){
						temp = true;
					}
				}
				if (temp == false){
					return new CircleBumper(opcode, x, y, this);
				} else 
					return null;
			} else {
				return new CircleBumper(opcode, x, y, this);
			}
		} else if (type == 't'){
			boolean temp = false;
			if (gizmoList.size() > 0){
				for (Coordinate c : occupiedSquares){
					if (c.x == x && c.y == y){
						temp = true;
					}
				}
				if (temp == false){
					return new TriangleBumper(opcode, x, y, this);
				} else
					return null;
			} else {
				return new TriangleBumper(opcode, x, y, this);
			}
		}
		return null;
	}

	@Override
	public IFlipper createFlipperOp(char type, String opcode, int x, int y) {
		setOccupiedSquares();
		if (type == 'l'){
			boolean temp = false;
			if (gizmoList.size() > 0){
				if (x < 19 && y < 19){
						for (int i = x; i < x + 2; i++){
							for (int j = y; j < y + 2; j++){
								for (Coordinate c : occupiedSquares){
									if (c.x == i && c.y == j){
										temp = true;
									}
								}
							}
						}
						if (temp == false){
							return new LeftFlipper(opcode, x, y, this);
						} else
							return null;
					} else {
					return null;
				}
			} else if (x < 19 && y < 19) {
				return new LeftFlipper(opcode,x, y, this);
			}
		} else if (type == 'r'){
			if (gizmoList.size() > 0){
				if (x > 0 && y < 19){
					boolean temp = false;
						for (int i = x; i < x + 2; i++){
							for (int j = y; j < y + 2; j++){
								for (Coordinate c : occupiedSquares){
									if (c.x == i && c.y == j){
										temp = true;
									}
								}
							}
						}
						if (temp == false){
							return new RightFlipper(opcode, x, y, this);
						} else
							return null;
					}
			} else if (x > 1 && y < 19){
				return new RightFlipper(opcode, x, y, this);
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public IAbsorber createAbsorberOp(String opcode, int x1, int y1, int x2, int y2) {
		setOccupiedSquares();
		if (gizmoList.size() > 0){
			boolean temp = false;
			for (int i = x1; i < x2; i++){
				for (int j = y1; j < y2; j++){
					for (Coordinate c : occupiedSquares){
						if (c.x == i && c.y == j){
							temp = true;
						}
					}
				}
			}
			if (temp == true){
				return null;
			} else {
				return new Absorber(opcode, x1, y1, x2, y2, this);
			}
		} else {
			return new Absorber(opcode, x1, y1, x2, y2, this);
		}
	}

	@Override
	public Ball createBallOp(String opcode, double x, double y, double vx, double vy) {
		
		double topLeftX = x;
		double topLeftY = y;
		setOccupiedSquares();
		
		boolean temp = true;
		if (gizmoList.size() > 0){
			for (Coordinate c : occupiedSquares){
				if (x > (c.x()-0.25) && x < (c.x()+1+0.25)){
					if (y > (c.y()-0.25) && y < (c.y()+1+0.25)){
						temp = false;
					}
				}
			}
			if (temp == true){
				return new Ball(opcode, x, y, vx, vy, this);
			}
		} else {
			return new Ball(opcode, x, y, vx, vy, this);
		}
		return null;
	}

}

/**
 * Created by ponomarevandrew on 22.06.17.
 */
public abstract class CollinearPoints {

    LineSegment[] segments;

    int numberOfSegments;

    void checkArgument(Point[] points) {
        // check if argument is null
        if (points == null) {
            throw new NullPointerException("argument is null");
        }
        //check if one of points is null
        for (Point p : points) {
            if (p == null)
                throw new NullPointerException("one of points is null");
        }

        for (Point p0 : points) {
            for (Point p1 : points) {
                if (p0 != p1 && p0.compareTo(p1) == 0) {
                    throw new IllegalArgumentException("the argument to the constructor contains a repeated point");
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }

    void deleteDuplicateFromArray(LineSegment[] segments) {
        int k = 0;
        for (int i = 0; i < segments.length; i++) {
            if (segments[i] == null) continue;
            k++;
            for (int j = i + 1; j < segments.length; j++) {
                if (segments[j] != null && segments[j].equals(segments[i]))
                    segments[j] = null;
            }
        }
        LineSegment[] tmp = new LineSegment[k];
        for (int i = 0, j = 0; i < segments.length; i++) {
            if (segments[i] != null) tmp[j++] = segments[i];
        }
        this.segments = tmp;
        numberOfSegments = k;
    }
}

import java.util.Arrays;

/**
 * Created by ponomarevandrew on 21.06.17.
 */
public class BruteCollinearPoints {

    private LineSegment[] segments;

    private int numberOfSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        checkArgument(points);

        // длина массива сегмнетов
        int length = 4;
        segments = new LineSegment[length];
        numberOfSegments = 0;

        for (Point p : points) {
            for (Point q : points) {
                if (p == q) continue;
                for (Point r : points) {
                    if (p == r || q == r) continue;
                    for (Point s : points) {
                        if (p == s || q == s || r == s) continue;
                        // берем всевозможные четверки неравных по ссылкам точек и смотрим 3 угла между ними
                        double slope1 = p.slopeTo(q);
                        double slope2 = p.slopeTo(r);
                        double slope3 = p.slopeTo(s);
                        // если находится 4 точки у которых углы равны
                        double eps = 0.0000001;
                        if (Math.abs(slope1 - slope2) < eps && Math.abs(slope2 - slope3) < eps) {
                            // если число сегментов равно длине массива то увеличиваем массив вдвое
                            if (numberOfSegments == length) {
                                length *= 2;
                                segments = Arrays.copyOf(segments, length);
                            }
                            //записываем наименьшую и наибольшую точки как новый сегмент в итоговый массив сегментов
                            Point[] segmentPoints = new Point[4];
                            segmentPoints[0] = p;
                            segmentPoints[1] = q;
                            segmentPoints[2] = r;
                            segmentPoints[3] = s;
                            Arrays.sort(segmentPoints);
                            segments[numberOfSegments] = new LineSegment(segmentPoints[0], segmentPoints[3]);
                            numberOfSegments++;
                        }
                    }
                }
            }
        }
        deleteDuplicateFromArray(segments);
    }

    private void checkArgument(Point[] points) {
        // check if argument is null
        if (points == null) {
            throw new NullPointerException("argument is null");
        }
        // check if one of points is null
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

    private void deleteDuplicateFromArray(LineSegment[] segmentArray) {
        int k = 0;
        for (int i = 0; i < segmentArray.length; i++) {
            if (segmentArray[i] == null) continue;
            k++;
            for (int j = i + 1; j < segmentArray.length; j++) {
                if (segmentArray[j] != null && segmentArray[j].equals(segmentArray[i]))
                    segmentArray[j] = null;
            }
        }
        LineSegment[] tmp = new LineSegment[k];
        for (int i = 0, j = 0; i < segmentArray.length; i++) {
            if (segmentArray[i] != null) tmp[j++] = segmentArray[i];
        }
        this.segments = tmp;
        numberOfSegments = k;
    }

}

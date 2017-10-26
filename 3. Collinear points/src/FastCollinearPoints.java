import java.util.Arrays;

/**
 * Created by ponomarevandrew on 22.06.17.
 */
public class FastCollinearPoints {

    private LineSegment[] segments;

    private int numberOfSegments;

    public FastCollinearPoints(Point[] points)  {

        checkArgument(points);

        // длина массивов сегментов
        int length = 4;
        segments = new LineSegment[length];
        numberOfSegments = 0;

        Point[] points2 = new Point[points.length];
        System.arraycopy(points, 0, points2, 0, points.length);
        for (Point origin : points) {
            // сортируем массив по возрастанию углов с источником
            Arrays.sort(points2, origin.slopeOrder());


            // инициализируем последнюю найденную точку сегмента
            Point last_segment_point = points2[0];
            // и длину сегмента с учетом точек равных источнику
            int segment_length = 2;
            int first_index = 1;

            // для точек не равных источнику смотрим
            for (int i = 1; i <= points2.length; i++) {
                double eps = 0.0000001;
                if (i == points2.length || Math.abs(points2[i].slopeTo(origin) - last_segment_point.slopeTo(origin)) > eps) {
                    // если его длина >=4
                    if (segment_length >= 4) {
                        // собираем все точки вместе не считая совпадающих с источником
                        if (numberOfSegments == length) {
                            length *= 2;
                            segments = Arrays.copyOf(segments, length);
                        }
                        numberOfSegments++;
                        Point[] one_line_points = new Point[segment_length];
                        one_line_points[0] = origin;
                        for (int j = 1; j < segment_length; j++) {
                            one_line_points[j] = points2[first_index + j - 1];
                        }
                        Arrays.sort(one_line_points);
                        LineSegment segment = new LineSegment(one_line_points[0], one_line_points[one_line_points.length - 1]);
                        segments[numberOfSegments++] = segment;
                    }
                    first_index = i;
                    segment_length = 2;
                    if (i != points2.length) {
                        last_segment_point = points2[i];
                    }
                }
                // если угол текущей совпадает с предыдущей точкой
                // значит сегмент еще не окончен
                else {
                    segment_length++;
                    last_segment_point = points2[i];
                    // в противном случае прошлый сегмент окончен
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

    private void deleteDuplicateFromArray(LineSegment[] segments) {
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

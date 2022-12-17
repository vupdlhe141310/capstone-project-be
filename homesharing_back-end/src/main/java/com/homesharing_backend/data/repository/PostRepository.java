package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.dto.PostDto;
import com.homesharing_backend.data.dto.PostTopRateDto;
import com.homesharing_backend.data.dto.SearchDto;
import com.homesharing_backend.data.dto.ViewBookingDto;
import com.homesharing_backend.data.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM Post order by id DESC LIMIT 4", nativeQuery = true)
    List<Post> getPostTop();

    Post getPostById(Long id);

    Boolean existsPostById(Long id);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.PostTopRateDto(p.id, pi.imageUrl, avg(r.point) , p.title) FROM Post p" +
            " left join PostImage pi on p.id = pi.post.id left join BookingDetail bd" +
            " on p.id = bd.post.id left join Rate r on r.bookingDetail.id=bd.id " +
            " group by p.id order by avg(r.point) desc ")
    List<PostTopRateDto> getTopPostByRate();

    @Query(value = "SELECT new com.homesharing_backend.data.dto.PostTopRateDto(p.id, pi.imageUrl, avg(r.point) , p.title) FROM Post p" +
            " left join PostImage pi on p.id = pi.post.id left join BookingDetail bd" +
            " on p.id = bd.post.id left join Rate r on r.bookingDetail.id=bd.id WHERE p.id =:postID " +
            " group by p.id order by avg(r.point) desc")
    Optional<PostTopRateDto> getPostDetailByPostID(@Param("postID") Long postID);

    List<Post> getPostByHost_Id(Long hostID);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.PostDto(p.id, p.title, pi.imageUrl, rp.id, rp.status," +
            "p.status, pm.status, pm.endDate, pm.startDate, avg(r.point)) FROM Post p " +
            "left join PostImage pi on p.id = pi.post.id " +
            "left join PostPayment  pm on p.id = pm.post.id " +
            "left join BookingDetail bd on p.id = bd.post.id " +
            "left join ReportPost rp on p.id = rp.post.id " +
            "left join Rate r on r.bookingDetail.id=bd.id WHERE p.host.id =:hostID " +
            "GROUP BY p.id order by avg(r.point) desc")
    public List<PostDto> getPostDTO(@Param("hostID") Long hostID);

    @Query("SELECT new com.homesharing_backend.data.dto.SearchDto(p.id, p.title, pd.address, p.price," +
            " pi.imageUrl, v.code, avg(r.point), p.host.typeAccount) FROM Post p " +
            "LEFT JOIN PostDetail pd on p.id = pd.post.id " +
            "LEFT join PostVoucher pv on p.id = pv.post.id " +
            "LEFT JOIN Voucher v on pv.voucher.id = v.id " +
            "LEFT JOIN PostImage pi on p.id = pi.post.id " +
            "LEFT JOIN BookingDetail bd on p.id = bd.post.id " +
            "LEFT JOIN Rate r on bd.id = r.bookingDetail.id WHERE p.title LIKE %:title% OR pd.address LIKE %:title% " +
            "GROUP BY p.id")
    Page<SearchDto> listSearchPostByTitle(@Param("title") String title, PageRequest pageRequest);

    @Query("SELECT new com.homesharing_backend.data.dto.SearchDto(p.id, p.title, pd.address, p.price," +
            " pi.imageUrl, v.code, avg(r.point), p.host.typeAccount) FROM Post p " +
            "LEFT JOIN PostDetail pd on p.id = pd.post.id " +
            "LEFT join PostVoucher pv on p.id = pv.post.id " +
            "LEFT JOIN Voucher v on pv.voucher.id = v.id " +
            "LEFT JOIN PostImage pi on p.id = pi.post.id " +
            "LEFT JOIN BookingDetail bd on p.id = bd.post.id " +
            "LEFT JOIN Rate r on bd.id = r.bookingDetail.id WHERE pd.address LIKE %:provinceName% " +
            "GROUP BY p.id")
    List<SearchDto> listSearchPostByProvince(@Param("provinceName") String provinceName);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.PostDto(p.id, p.title, p.status, avg(r.point)) from Post p " +
            "LEFT JOIN PostDetail pd ON p.id = pd.post.id " +
            "LEFT JOIN BookingDetail bd ON p.id = bd.post.id " +
            "LEFT JOIN Rate r ON bd.id = r.bookingDetail.id WHERE p.host.id= :hostID " +
            "GROUP BY p.id ")
    Page<PostDto> listPostByHost(@Param("hostID") Long hostID, PageRequest pageRequest);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.ViewBookingDto(b.id, bd.id, p.id, p.title, bd.startDate, " +
            "bd.endDate, b.totalMoney, bd.totalPerson, bd.totalService, b.note, b.status) FROM Booking b\n" +
            "left join BookingDetail bd on b.id = bd.booking.id\n" +
            "left join Post p on bd.post.id = p.id where b.status = :status and p.host.id= :hostID \n" +
            "group by b.id\n")
    Page<ViewBookingDto> getAllStatusBooking(@Param("status") int status, @Param("hostID") Long hostID, PageRequest pageRequest);

    Page<Post> getPostByHost_Id(Long hostID, PageRequest pageRequest);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.ViewBookingDto(b.id, bd.id, p.id, p.title, bd.startDate, " +
            "bd.endDate, b.totalMoney, bd.totalPerson, bd.totalService, b.note, b.status) FROM Booking b " +
            "LEFT JOIN BookingDetail bd on b.id = bd.booking.id " +
            "LEFT JOIN Post p on bd.post.id = p.id WHERE p.host.id= :hostID AND b.status= 2 AND (current_date() between bd.startDate AND bd.endDate)")
    Page<ViewBookingDto> getAllCurrentBooking(@Param("hostID") Long hostID, PageRequest pageRequest);

    @Query(value = "SELECT new com.homesharing_backend.data.dto.SearchDto(p.id, p.title, pd.address, p.price, pi.imageUrl, v.description, " +
            "avg(r.point), h.typeAccount, h.user.userDetail.fullName) FROM Post p " +
            "LEFT JOIN PostDetail pd ON p.id = pd.post.id " +
            "LEFT JOIN PostImage pi ON p.id = pi.post.id " +
            "LEFT JOIN PostServices ps ON p.id = ps.post.id " +
            "LEFT JOIN PostVoucher pv ON p.id = pv.post.id " +
            "LEFT JOIN Voucher v ON pv.voucher.id = v.id " +
            "LEFT JOIN Host h ON p.host.id = h.id " +
            "LEFT JOIN User u ON h.user.id = u.id " +
            "LEFT JOIN UserDetail ud ON u.userDetail.userDetailId = ud.userDetailId " +
            "LEFT JOIN BookingDetail bd ON p.id = bd.post.id " +
            "LEFT JOIN Rate r ON bd.id = r.bookingDetail.id " +
            "WHERE (v.percent IN :percent) AND (p.price BETWEEN :minPrice AND :maxPrice) " +
            "AND (pd.roomType.id IN :listRoomTypeID) AND (pd.guestNumber = :guestNumber) " +
            "AND (ps.services.id IN :listServiceID) AND (bd.startDate <> :starDate) GROUP BY p.id " +
            "HAVING (avg(r.point) BETWEEN :minStar AND :maxStar)")
    Page<SearchDto> getSearchFilter(@Param("percent") List<Integer> percent, @Param("minPrice") float minPrice, @Param("maxPrice") float maxPrice,
                                    @Param("listRoomTypeID") List<Long> listRoomTypeID, @Param("guestNumber") int guestNumber,
                                    @Param("listServiceID") List<Long> listServiceID, @Param("minStar") double minStar, @Param("maxStar") double maxStar,
                                    @Param("starDate") Date starDate, PageRequest pageRequest);
}

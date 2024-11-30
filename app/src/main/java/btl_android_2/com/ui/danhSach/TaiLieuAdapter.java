package btl_android_2.com.ui.danhSach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import btl_android_2.com.R;

public class TaiLieuAdapter extends RecyclerView.Adapter<TaiLieuAdapter.TaiLieuViewHolder> {

    private Context context;
    private List<TaiLieu> taiLieuList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(TaiLieu taiLieu);
    }

    public TaiLieuAdapter(Context context, List<TaiLieu> taiLieuList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.taiLieuList = taiLieuList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TaiLieuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tailieu, parent, false);
        return new TaiLieuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaiLieuViewHolder holder, int position) {
        TaiLieu taiLieu = taiLieuList.get(position);
        holder.txtTieuDe.setText(taiLieu.getTieuDe());
//        holder.txtTacGia.setText(taiLieu.getTacGia());
        holder.txtMoTa.setText(taiLieu.getMoTa());
//        holder.txtNoiDung.setText(taiLieu.getNoiDung());
        holder.txtGia.setText(taiLieu.isFree() ? "Miễn phí" : taiLieu.getGia() + " VND");

        holder.btnXemChiTiet.setOnClickListener(v -> onItemClickListener.onItemClick(taiLieu));
    }

    @Override
    public int getItemCount() {
        return taiLieuList.size();
    }

    public static class TaiLieuViewHolder extends RecyclerView.ViewHolder {
        TextView txtTieuDe, txtTacGia, txtMoTa, txtNoiDung, txtGia;
        Button btnXemChiTiet;

        public TaiLieuViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTieuDe = itemView.findViewById(R.id.tieuDe);
//            txtTacGia = itemView.findViewById(R.id.tacGia);
            txtMoTa = itemView.findViewById(R.id.moTa);
//            txtNoiDung = itemView.findViewById(R.id.noiDung);
            txtGia = itemView.findViewById(R.id.gia);
            btnXemChiTiet = itemView.findViewById(R.id.btnXemChiTiet);
        }
    }
}

